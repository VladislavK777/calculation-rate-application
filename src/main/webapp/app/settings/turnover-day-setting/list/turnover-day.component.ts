import {Component, OnInit} from '@angular/core';
import {TurnoverDay} from "../service/turnover-day.model";
import {TurnoverDayService} from "../service/turnover-day.service";

@Component({
  selector: 'jhi-turnover-day',
  templateUrl: './turnover-day.component.html',
})
export class TurnoverDayComponent implements OnInit {
  turnoverDays: TurnoverDay[] | null = null;
  isLoading = false;

  constructor(private turnoverDayService: TurnoverDayService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;
    this.turnoverDayService.findAll()
      .subscribe(response => {
        this.isLoading = false;
        this.turnoverDays = response
      });
  }
}
