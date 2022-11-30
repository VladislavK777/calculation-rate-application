import {Road} from "./road.model";

export interface Department {
  id: number;
  code: string;
  name: string;
  road: Road;
}
