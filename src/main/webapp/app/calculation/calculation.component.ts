import {Component, OnInit} from '@angular/core';
import {CalculationService} from "./calculation.service";
import {Station} from "../settings/station-setting/service/station.model";
import {Cargo} from "../settings/common-sevice/model/cargo.model";
import {map, Observable, startWith} from 'rxjs';
import {FormControl, FormGroup} from '@angular/forms';
import saveAs from 'file-saver';
import {DatePipe} from '@angular/common';
import {StationService} from "../settings/station-setting/service/station.service";
import {CargoService} from "../settings/common-sevice/cargo.service";
import {ExportModel} from "./model/export-model.model";
import {CargoVolume} from "../settings/common-sevice/model/cargo-volume.model";
import {CargoVolumeService} from "../settings/common-sevice/carg-volume.service";

@Component({
  selector: 'jhi-calculation',
  templateUrl: './calculation.component.html',
  styleUrls: ['./calculation.component.scss'],
})
export class CalculationComponent implements OnInit {
  totalRouts!: ExportModel[];
  messageError: string | null = null;
  stationsFrom!: Observable<Station[]>;
  stationsTo!: Observable<Station[]>;
  cargoVolume: CargoVolume[] | null = [];
  cargos!: Observable<Cargo[]>;
  stationFrom: Station | null = null;
  stationTo: Station | null = null;
  cargo: Cargo | null = null;
  isSelectedFile: boolean = false;

  fromControl = new FormGroup({
    stationFrom: new FormControl('', {nonNullable: true}),
    stationTo: new FormControl('', {nonNullable: true}),
    cargo: new FormControl('', {nonNullable: true}),
    cargoVolume: new FormControl('', {nonNullable: true})
  });

  fileForm = new FormGroup({
    fileRouts: new FormControl()
  });

  constructor(private calculationService: CalculationService,
              private stationService: StationService,
              private cargoService: CargoService,
              private cargoVolumeService: CargoVolumeService,
              private datePipe: DatePipe) {

  }

  ngOnInit() {
    this.clean();
    this.cargoVolumeService.findAll().subscribe(response => this.cargoVolume = response);
    this.fromControl.get('stationFrom')?.valueChanges.pipe(
      startWith(''),
      map(value => this.stationService
        .findAllByFilter({filter: value || '', roadId: null}))
    ).subscribe(stations => this.stationsFrom = stations);
    this.fromControl.get('stationTo')?.valueChanges.pipe(
      startWith(''),
      map(value => this.stationService
        .findAllByFilter({filter: value || '', roadId: null}))
    ).subscribe(stations => this.stationsTo = stations);
    this.fromControl.get('cargo')?.valueChanges.pipe(
      startWith(''),
      map(value => this.cargoService
        .searchCargo({filter: value || '', roadId: null}))
    ).subscribe(cargos => this.cargos = cargos);
  }

  selectedStationFrom(e: any) {
    this.stationFrom = e.option.value;
    this.fromControl.get('stationFrom')?.setValue(this.stationFrom?.name || '');
  }

  selectedStationTo(e: any) {
    this.stationTo = e.option.value;
    this.fromControl.get('stationTo')?.setValue(this.stationTo?.name || '');
  }

  selectedCargo(e: any) {
    this.cargo = e.option.value;
    this.fromControl.get('cargo')?.setValue(this.cargo?.name || '');
  }

  selectedFile(e: any) {
    const reader = new FileReader();
    if (e.target.files && e.target.files.length) {
      const file = e.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.fileForm.get('fileRouts')?.setValue(file);
        this.isSelectedFile = true;
      }
    }
  }

  getGroupRouts() {
    this.messageError = null;
    let formData: FormData = new FormData()
    formData.append("fileRouts", this.fileForm.value.fileRouts);
    this.calculationService
      .getGroupRouts(formData).subscribe(
        response => this.totalRouts = response,
        e => this.messageError = e.error.title
      );
  }

  getRout() {
    this.messageError = null;
    this.calculationService
      .getRout({
        stationFromCode: this.stationFrom?.code,
        stationToCode: this.stationTo?.code,
        cargoCode: this.cargo?.code,
        cargoVolumeValue: this.fromControl.get('cargoVolume')?.value
      })
      .subscribe(response =>
          this.totalRouts.push(response),
        e => this.messageError = e.error.title
      );
  }

  downloadFile() {
    this.calculationService.downloadsExcel(this.totalRouts).subscribe(response => {
      let blob = new Blob([response], {type: "application/vnd.ms-excel"});
      const currentDate = this.datePipe.transform(new Date(), 'dd.MM.yyyy HH.mm');
      saveAs(blob, 'routs_' + currentDate + '.xlsx');
    });
  }

  clean() {
    this.totalRouts = [];
    this.stationFrom = null;
    this.stationTo = null;
    this.cargo = null;
    this.fromControl.get('stationFrom')?.setValue('');
    this.fromControl.get('stationTo')?.setValue('');
    this.fromControl.get('cargo')?.setValue('');
    this.fromControl.get('cargoVolume')?.setValue('');
  }

  checkFillFields(): boolean {
    return (this.totalRouts.length != 0) || (this.stationFrom != null || this.stationTo != null || this.cargo != null
      || this.fromControl.get('cargoVolume')?.value != '');
  }

  checkFillAllFields(): boolean {
    return this.stationFrom != null && this.stationTo != null && this.cargo != null
      && this.fromControl.get('cargoVolume')?.value != '';
  }
}

