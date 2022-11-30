import {Component, OnInit} from '@angular/core';
import {Profit} from "../service/profit.model";
import {ProfitService} from "../service/profit.service";

@Component({
  selector: 'jhi-profit',
  templateUrl: './profit.component.html',
})
export class ProfitComponent implements OnInit {
  profits: Profit[] | null = null;
  isLoading = false;

  constructor(private profitService: ProfitService) {
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;
    this.profitService.findAll()
      .subscribe(response => {
        this.isLoading = false;
        this.profits = response
      });
  }
}
