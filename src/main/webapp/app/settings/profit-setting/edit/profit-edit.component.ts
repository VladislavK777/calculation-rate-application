import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Profit} from "../service/profit.model";
import {ProfitService} from "../service/profit.service";
import {CargoVolume} from "../../common-sevice/model/cargo-volume.model";

const profitTemplate = {} as Profit;

@Component({
  selector: 'jhi-profit-edit',
  templateUrl: './profit-edit.component.html',
  styleUrls: ['./profit-edit.component.scss'],
})
export class ProfitEditComponent implements OnInit {
  messageError: string | null = null;
  isSaving = false;
  profit: Profit | null = null;

  editForm = new FormGroup({
    id: new FormControl(profitTemplate.id, {nonNullable: true}),
    cargoVolume: new FormControl(profitTemplate.cargoVolume?.value, {nonNullable: true}),
    value: new FormControl(profitTemplate.value, {nonNullable: true}),
  });

  constructor(private profitService: ProfitService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.messageError = null;
    this.route.data.subscribe(({profit}) => {
      this.editForm.reset(profit);
      this.profit = profit;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.messageError = null;
    this.isSaving = true;
    this.profitService.update({id: this.editForm.value.id, cargoVolume: this.editForm.value.cargoVolume as unknown as CargoVolume, value: this.editForm.value.value} as Profit).subscribe({
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
