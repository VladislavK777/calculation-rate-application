import {Department} from "../../common-sevice/model/department.model";
import {Road} from "../../common-sevice/model/road.model";
import {Station} from "../../station-setting/service/station.model";
import {CargoVolume} from "../../common-sevice/model/cargo-volume.model";
import {Cargo} from "../../common-sevice/model/cargo.model";
import {CargoClass} from "../../common-sevice/model/cargo-classes.model";

export interface ReturnException {
  id: number;
  num: number;
  roads: Road[];
  stations: Station[] | null;
  departments: Department[] | null;
  cargoVolumes: CargoVolume[];
  cargoClasses: CargoClass[];
  stationReturn: Station | null;
  stationFrom: Station | null;
  stationTo: Station | null;
  cargo: Cargo | null;
  cargoFlightType: string;
  travelTime: number | null;
  loadUnload: number | null;
  distance: number | null;
  rate: number | null;
  tariff: number | null;
}
