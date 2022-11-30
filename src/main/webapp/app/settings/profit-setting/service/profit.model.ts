import {CargoVolume} from "../../common-sevice/model/cargo-volume.model";

export interface Profit {
  id: number;
  cargoVolume: CargoVolume;
  value: number;
}
