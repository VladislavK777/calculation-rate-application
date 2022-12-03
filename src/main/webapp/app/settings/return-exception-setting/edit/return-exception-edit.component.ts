import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Road} from "../../common-sevice/model/road.model";
import {Department} from "../../common-sevice/model/department.model";
import {map, Observable, startWith} from 'rxjs';
import {Station} from "../../station-setting/service/station.model";
import {StationService} from "../../station-setting/service/station.service";
import {Cargo} from "../../common-sevice/model/cargo.model";
import {CargoService} from "../../common-sevice/cargo.service";
import {RoadService} from "../../common-sevice/road.service";
import {CargoVolume} from "../../common-sevice/model/cargo-volume.model";
import {DepartmentService} from "../../common-sevice/department.service";
import {CargoClassService} from "../../common-sevice/carg-class.service";
import {CargoClass} from "../../common-sevice/model/cargo-classes.model";
import {CargoVolumeService} from "../../common-sevice/carg-volume.service";
import {CargoFlightType} from "../../common-sevice/model/cargo-flight-type.model";
import {ReturnException} from "../service/return-exception.model";
import {ReturnExceptionService} from "../service/return-exception.service";

const returnExceptionTemplate = {} as ReturnException;

@Component({
  selector: 'jhi-return-exception-edit',
  templateUrl: './return-exception-edit.component.html',
  styleUrls: ['./return-exception-edit.component.scss'],
})
export class ReturnExceptionEditComponent implements OnInit {
  messageError: string | null = null;
  isSaving = false;
  returnException: ReturnException | null = null;

  roadList: Road[] = [];
  departmentList: Department[] = [];
  stationList: Station[] = [];
  cargoVolumeList: CargoVolume[] = [];
  cargoClassList: CargoClass[] = [];

  stationsReturn!: Observable<Station[]>;
  stationsFrom!: Observable<Station[]>;
  stationsTo!: Observable<Station[]>;
  cargos!: Observable<Cargo[]>;

  stationReturn: Station | null = null;
  stationFrom: Station | null = null;
  stationTo: Station | null = null;
  cargo: Cargo | null = null;

  selectedRoadIds: number[] = [];
  selectedDepartmentIds: number[] = [];
  selectedStationIds: number[] = [];
  selectedCargoVolumeValues: number[] = [];
  selectedCargoClassValues: number[] = [];

  cargoType = ['ГРУЖ', 'ПОР'];

  editForm = new FormGroup({
    id: new FormControl(returnExceptionTemplate.id, {nonNullable: true}),
    num: new FormControl(returnExceptionTemplate.num, {nonNullable: true}),
    stations: new FormControl(),
    roads: new FormControl(),
    departments: new FormControl(),
    cargoVolumes: new FormControl(),
    cargoClasses: new FormControl(),
    stationReturn: new FormControl(returnExceptionTemplate.stationReturn?.name, {nonNullable: true}),
    stationFrom: new FormControl(returnExceptionTemplate.stationFrom?.name, {nonNullable: true}),
    stationTo: new FormControl(returnExceptionTemplate.stationTo?.name, {nonNullable: true}),
    cargo: new FormControl(returnExceptionTemplate.cargo?.name, {nonNullable: true}),
    cargoFlightType: new FormControl(returnExceptionTemplate.cargoFlightType, {nonNullable: true}),
    travelTime: new FormControl(returnExceptionTemplate.travelTime, {nonNullable: true}),
    loadUnload: new FormControl(returnExceptionTemplate.loadUnload, {nonNullable: true}),
    distance: new FormControl(returnExceptionTemplate.distance, {nonNullable: true}),
    rate: new FormControl(returnExceptionTemplate.rate, {nonNullable: true}),
    tariff: new FormControl(returnExceptionTemplate.tariff, {nonNullable: true}),
  });

  constructor(
    private returnExceptionService: ReturnExceptionService,
    private stationService: StationService,
    private cargoService: CargoService,
    private roadService: RoadService,
    private departmentService: DepartmentService,
    private cargoClassService: CargoClassService,
    private cargoVolumeService: CargoVolumeService,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.roadService.findAll().subscribe(response => this.roadList = response);
    this.route.data.subscribe(({returnException}) => {
      if (returnException) {
        this.editForm.reset({
          id: returnException.id,
          num: returnException.num,
          stationReturn: returnException.stationReturn?.name,
          stationFrom: returnException.stationFrom?.name,
          stationTo: returnException.stationTo?.name,
          cargo: returnException.cargo?.name,
          cargoFlightType: this.getCargoFlightType(returnException.cargoFlightType),
          travelTime: returnException.travelTime,
          loadUnload: returnException.loadUnload,
          distance: returnException.distance,
          rate: returnException.rate,
          tariff: returnException.tariff
        });
        this.returnException = returnException;
        this.setRoads(returnException);
        this.setDepartments(returnException);
        this.setStations(returnException);
        this.setCargoClasses(returnException);
        this.setCargoVolumes(returnException);
        this.stationReturn = returnException.stationReturn;
        this.stationFrom = returnException.stationFrom;
        this.stationTo = returnException.stationTo;
        this.cargo = returnException.cargo;
      }
    });
    if (this.selectedStationIds.length != 0) {
      this.selectedStationIds.forEach((id) => {
        this.stationList = [];
        this.stationService.findById(id).subscribe(response => this.stationList.push(response));
      })
    } else {
      this.stationService.findAllByFilter({
        filter: '',
        roadId: this.selectedRoadIds
      }).subscribe(response => this.stationList = response);
    }
    this.departmentService.findAllByFilter({
      filter: '',
      roadId: this.selectedRoadIds
    }).subscribe(response => this.departmentList = response);
    this.cargoClassService.findAll().subscribe(response => {
      this.cargoClassList = response;
      if (this.returnException == null)
        this.setCargoClassesFromServer(response);
    });
    this.cargoVolumeService.findAll().subscribe(response => {
      this.cargoVolumeList = response;
      if (this.returnException == null)
        this.setCargoVolumesFromServer(response);
    });

    this.editForm.get('stationReturn')?.valueChanges.pipe(
      startWith(''),
      map(value => this.stationService
        .findAllByFilter({filter: value || '', roadId: null}))
    ).subscribe(stations => this.stationsReturn = stations);
    this.editForm.get('stationFrom')?.valueChanges.pipe(
      startWith(''),
      map(value => this.stationService
        .findAllByFilter({filter: value || '', roadId: null}))
    ).subscribe(stations => this.stationsFrom = stations);
    this.editForm.get('stationTo')?.valueChanges.pipe(
      startWith(''),
      map(value => this.stationService
        .findAllByFilter({filter: value || '', roadId: null}))
    ).subscribe(stations => this.stationsTo = stations);
    this.editForm.get('cargo')?.valueChanges.pipe(
      startWith(''),
      map(value => this.cargoService
        .searchCargo({filter: value || '', roadId: null}))
    ).subscribe(cargos => this.cargos = cargos);
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.returnExceptionService.update(
      {
        id: this.editForm.value.id,
        num: this.editForm.value.num,
        stations: this.putStations(),
        roads: this.putRoads(),
        departments: this.putDepartments(),
        cargoVolumes: this.putCargoVolumes(),
        cargoClasses: this.putCargoClasses(),
        stationReturn: this.stationReturn,
        stationFrom: this.stationFrom,
        stationTo: this.stationTo,
        cargo: this.cargo,
        cargoFlightType: this.editForm.value.cargoFlightType != null ? this.getCargoFlightTypeEnum(this.editForm.value.cargoFlightType) : 'EMPTY',
        travelTime: this.editForm.value.travelTime,
        loadUnload: this.editForm.value.loadUnload,
        distance: this.editForm.value.distance,
        rate: this.editForm.value.rate,
        tariff: this.editForm.value.tariff,
      } as ReturnException).subscribe({
      next: () => this.onSaveSuccess(),
      error: (e) => {
        this.onSaveError();
        this.messageError = e.error.title;
        window.scrollTo(pageYOffset, 0);
      }
    });
  }

  selectedRoadId(roadId: number) {
    if (this.selectedRoadIds.includes(roadId))
      this.selectedRoadIds = this.selectedRoadIds.filter(id => id != roadId);
    else
      this.selectedRoadIds.push(roadId);
    this.departmentService.findAllByFilter({
      filter: '',
      roadId: this.selectedRoadIds
    }).subscribe(response => this.departmentList = response);
    this.editForm.get('departments')?.setValue(this.selectedDepartmentIds);
  }

  selectedDepartmentId(departmentId: number) {
    if (this.selectedDepartmentIds.includes(departmentId))
      this.selectedDepartmentIds = this.selectedDepartmentIds.filter(id => id != departmentId);
    else
      this.selectedDepartmentIds.push(departmentId);
    this.editForm.get('departments')?.setValue(this.selectedDepartmentIds);
  }

  searchDepartmentsByInput(e: any) {
    this.departmentService.findAllByFilter({
      filter: (e.target.value || ''),
      roadId: this.selectedRoadIds
    }).subscribe(response => this.departmentList = response);
    this.editForm.get('departments')?.setValue(this.selectedDepartmentIds);
  }

  openedChangeDepartment(opened: boolean) {
    if (!opened) {
      this.departmentService.findAllByFilter({
        filter: '',
        roadId: this.selectedRoadIds
      }).subscribe(response => this.departmentList = response);
    }
  }

  selectedStationId(stationId: number) {
    if (this.selectedStationIds.includes(stationId))
      this.selectedStationIds = this.selectedStationIds.filter(id => id != stationId);
    else
      this.selectedStationIds.push(stationId);
    this.editForm.get('stations')?.setValue(this.selectedStationIds);
  }

  searchStationsByInput(e: any) {
    this.stationService.findAllByFilter({
      filter: (e.target.value || ''),
      roadId: this.selectedRoadIds
    }).subscribe(response => this.stationList = response);
    this.editForm.get('stations')?.setValue(this.selectedStationIds);
  }

  openedChangeStation(opened: boolean) {
    if (!opened) {
      this.selectedStationIds.forEach((id) => {
        this.stationList = [];
        this.stationService.findById(id).subscribe(response => this.stationList.push(response));
      })
    } else {
      this.stationService.findAllByFilter({
        filter: '',
        roadId: this.selectedRoadIds
      }).subscribe(response => this.stationList = response);
    }
  }

  selectedCargoVolumeValue(volumeValue: number) {
    if (this.selectedCargoVolumeValues.includes(volumeValue))
      this.selectedCargoVolumeValues = this.selectedCargoVolumeValues.filter(value => value != volumeValue);
    else
      this.selectedCargoVolumeValues.push(volumeValue);
  }

  selectedCargoClassValue(classClass: number) {
    if (this.selectedCargoClassValues.includes(classClass))
      this.selectedCargoClassValues = this.selectedCargoClassValues.filter(value => value != classClass);
    else
      this.selectedCargoClassValues.push(classClass);
  }

  selectedStationReturn(e: any) {
    this.stationReturn = e.option.value;
    this.editForm.get('stationReturn')?.setValue(this.stationReturn?.name || '');
  }

  selectedStationFrom(e: any) {
    this.stationFrom = e.option.value;
    this.editForm.get('stationFrom')?.setValue(this.stationFrom?.name || '');
  }

  selectedStationTo(e: any) {
    this.stationTo = e.option.value;
    this.editForm.get('stationTo')?.setValue(this.stationTo?.name || '');
  }

  selectedCargo(e: any) {
    this.cargo = e.option.value;
    this.editForm.get('cargo')?.setValue(this.cargo?.name || '');
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  private setRoads(beginException: any) {
    let selectedRoads: Road[] = beginException.roads;
    selectedRoads.forEach((road) => this.selectedRoadIds.push(road.id));
    this.editForm.get('roads')?.setValue(this.selectedRoadIds);
  }

  private setDepartments(beginException: any) {
    let selectedDepartments: Department[] = beginException.departments;
    selectedDepartments.forEach((department) => this.selectedDepartmentIds.push(department.id));
    this.editForm.get('departments')?.setValue(this.selectedDepartmentIds);
  }

  private setStations(beginException: any) {
    let selectedStations: Station[] = beginException.stations;
    selectedStations.forEach((station) => this.selectedStationIds.push(station.id));
    this.editForm.get('stations')?.setValue(this.selectedStationIds);
  }

  private setCargoVolumes(beginException: any) {
    let selectedCargoVolumes: CargoVolume[] = beginException.cargoVolumes;
    selectedCargoVolumes.forEach((volume) => this.selectedCargoVolumeValues.push(volume.value));
    this.editForm.get('cargoVolumes')?.setValue(this.selectedCargoVolumeValues);
  }

  private setCargoVolumesFromServer(cargoVolumes: CargoVolume[]) {
    cargoVolumes.forEach((volume) => this.selectedCargoVolumeValues.push(volume.value));
    this.editForm.get('cargoVolumes')?.setValue(this.selectedCargoVolumeValues);
  }

  private setCargoClasses(beginException: any) {
    let selectedCargoClasses: CargoClass[] = beginException.cargoClasses;
    selectedCargoClasses.forEach((classes) => this.selectedCargoClassValues.push(classes.value));
    this.editForm.get('cargoClasses')?.setValue(this.selectedCargoClassValues);
  }

  private setCargoClassesFromServer(cargoClasses: CargoClass[]) {
    cargoClasses.forEach((volume) => volume.value != 0 ? this.selectedCargoClassValues.push(volume.value) : null);
    this.editForm.get('cargoClasses')?.setValue(this.selectedCargoClassValues);
  }

  private putRoads(): Road[] {
    let putRoadList: Road[] = [];
    this.selectedRoadIds.forEach(id => {
      putRoadList.push(...this.roadList.filter((value) => value.id == id));
    });
    return putRoadList;
  }

  private putDepartments(): Department[] {
    let putDepartmentList: Department[] = [];
    this.selectedDepartmentIds.forEach(id => {
      putDepartmentList.push(...this.departmentList.filter((value) => value.id == id));
    });
    return putDepartmentList;
  }

  private putStations(): Station[] {
    let putStationList: Station[] = [];
    this.selectedStationIds.forEach(id => {
      putStationList.push(...this.stationList.filter((value) => value.id == id));
    });
    return putStationList;
  }

  private putCargoVolumes(): CargoVolume[] {
    let putCargoVolumeList: CargoVolume[] = [];
    this.selectedCargoVolumeValues.forEach(valueVolume => {
      putCargoVolumeList.push(...this.cargoVolumeList.filter((value) => value.value == valueVolume));
    });
    return putCargoVolumeList;
  }

  private putCargoClasses(): CargoClass[] {
    let putCargoClassList: CargoVolume[] = [];
    this.selectedCargoClassValues.forEach(valueClass => {
      putCargoClassList.push(...this.cargoClassList.filter((value) => value.value == valueClass));
    });
    return putCargoClassList;
  }

  private getCargoFlightType(value: string): string {
    if ('FULL' === value)
      return CargoFlightType.FULL;
    else
      return CargoFlightType.EMPTY;
  }

  private getCargoFlightTypeEnum(value: string): string {
    if ('ГРУЖ' === value)
      return "FULL";
    else
      return "EMPTY";
  }
}
