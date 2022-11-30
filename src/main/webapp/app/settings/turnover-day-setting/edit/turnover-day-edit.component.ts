import {Component, OnInit} from '@angular/core';
import {TurnoverDay} from "../service/turnover-day.model";
import {FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {TurnoverDayService} from "../service/turnover-day.service";

const turnoverDayTemplate = {} as TurnoverDay;

@Component({
  selector: 'jhi-turnover-day-edit',
  templateUrl: './turnover-day-edit.component.html',
  styleUrls: ['./turnover-day-edit.component.scss'],
})
export class TurnoverDayEditComponent implements OnInit {
  messageError: string | null = null;
  isSaving = false;
  turnoverDay!: TurnoverDay;

  editForm = new FormGroup({
    id: new FormControl(turnoverDayTemplate.id, {nonNullable: true}),
    key: new FormControl(turnoverDayTemplate.key, {nonNullable: true}),
    value: new FormControl(turnoverDayTemplate.value, {nonNullable: true}),
  });

  constructor(private turnoverDayService: TurnoverDayService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({turnoverDay}) => {
      this.editForm.reset(turnoverDay);
      this.turnoverDay = turnoverDay;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.messageError = null;
    this.isSaving = true;
    this.turnoverDayService.update(this.editForm.getRawValue()).subscribe({
      next: () => this.onSaveSuccess(),
      error: (e) => {
        this.onSaveError();
        this.messageError = e.error.title;
        window.scrollTo(pageYOffset, 0);
      }
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
