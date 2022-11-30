import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Station} from "../service/station.model";
import {StationService} from "../service/station.service";

@Component({
  selector: 'jhi-station',
  templateUrl: './station.component.html',
})
export class StationComponent implements OnInit {
  stations: Station[] | null = null;

  constructor(private stationService: StationService) {
  }

  searchControl = new FormGroup({
    stationSearch: new FormControl('')
  });

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.stationService.findAllByFilter({filter: this.searchControl.value.stationSearch || '', roadId: null})
      .subscribe(response => this.stations = response);
  }
}
