import {Component, OnInit} from '@angular/core';
import {BeginException} from "../service/begin-exception.model";
import {BeginExceptionService} from "../service/begin-exception.service";
import {BeginExceptionDeleteDialogComponent} from "../delete/begin-exception-delete-dialog.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CargoFlightType} from "../../common-sevice/model/cargo-flight-type.model";

@Component({
  selector: 'jhi-begin-exception',
  templateUrl: './begin-exception.component.html',
})
export class BeginExceptionComponent implements OnInit {
  messageError: string | null = null;
  isLoading = false;
  beginExceptions: BeginException[] | null = null;

  constructor(private beginExceptionService: BeginExceptionService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.messageError = null;
    this.isLoading = false;
    this.beginExceptionService.findAll().subscribe(
      response => this.beginExceptions = response,
      e => this.messageError = e.error.title
    );
  }

  delete(beginException: BeginException): void {
    const modalRef = this.modalService.open(BeginExceptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.beginException = beginException;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  public getCargoFlightType(value: string): string {
    if ('FULL' === value)
      return CargoFlightType.FULL;
    else
      return CargoFlightType.EMPTY;
  }
}
