export class RequestRout {
  constructor(
    public stationFromCode?: string,
    public stationToCode?: string,
    public cargoCode?: string,
    public cargoVolumeValue?: string
  ) {
  }
}
