export interface Rout {
  num: number;
  stationFromName: string;
  roadFromNameShort: string;
  stationToName: string;
  roadToNameShort: string;
  cargoFlightTypeName: string;
  cargoName: string;
  distance: number;
  travelTime: number;
  loadUnload: number;
  fullCountDays: number;
  rate: number;
  tariff: number;
  requestRout: boolean;
}
