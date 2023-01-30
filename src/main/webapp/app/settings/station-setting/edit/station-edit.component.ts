import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {StationService} from "../service/station.service";
import {Station} from "../service/station.model";
import {Road} from "../../common-sevice/model/road.model";
import {RoadService} from "../../common-sevice/road.service";
import {Department} from "../../common-sevice/model/department.model";
import {DepartmentService} from "../../common-sevice/department.service";
import {Observable} from 'rxjs';

const stationTemplate = {} as Station;
const newStation: Station = {} as Station;

@Component({
  selector: 'jhi-station-edit',
  templateUrl: './station-edit.component.html',
  styleUrls: ['./station-edit.component.scss'],
})
export class StationEditComponent implements OnInit {
  messageError: string | null = null;
  isSaving = false;
  station!: Station;
  roads!: Observable<Road[]>;
  road: Road = {} as Road;
  departments!: Observable<Department[]>;
  department: Department = {} as Department;

  editForm = new FormGroup({
    id: new FormControl(stationTemplate.id, {nonNullable: true}),
    code: new FormControl(stationTemplate.code, {nonNullable: true}),
    name: new FormControl(stationTemplate.name, {nonNullable: true}),
    road: new FormControl(),
    department: new FormControl(),
  });

  constructor(private stationService: StationService,
              private roadService: RoadService,
              private departmentService: DepartmentService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ station }) => {
      if (station) {
        this.editForm.reset({id: station.id, code: station.code, name: station.name, road: station.road.name, department: station.department?.name});
        this.road = station.road;
        this.department = station.department;
        this.station = station;
        this.departments = this.departmentService.findAllByRoadId(station.road.id);
      } else {
        this.editForm.reset({id: newStation.id, code: newStation.code, name: newStation.name, road: newStation.road?.name, department: newStation.department?.name});
        this.station = newStation;
      }
    });
    this.roads = this.roadService.findAll();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.messageError = null;
    this.isSaving = true;
    this.stationService.update(
      {
        id: this.editForm.value.id,
        code: this.editForm.value.code,
        name: this.editForm.value.name,
        road: this.road.id == null ? null : {id: this.road.id},
        department: this.department.id == null ? null : {id: this.department.id}
      } as Station).subscribe({
      next: () => this.onSaveSuccess(),
      error: (e) => {
        this.onSaveError();
        this.messageError = e.error.title;
        window.scrollTo(pageYOffset, 0);
      },
    });
  }

  selectedRoad(e: any) {
    this.road = e.option.value;
    this.editForm.get('department')?.setValue('');
    this.departments = this.departmentService.findAllByRoadId(this.road.id);
  }

  selectedDepartment(e: any) {
    this.department = e.option.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
