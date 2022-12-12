import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CargoFlightType} from "../../common-sevice/model/cargo-flight-type.model";
import {ReturnException} from "../service/return-exception.model";
import {ReturnExceptionService} from "../service/return-exception.service";
import {ReturnExceptionDeleteDialogComponent} from "../delete/return-exception-delete-dialog.component";

@Component({
  selector: 'jhi-return-exception',
  templateUrl: './return-exception.component.html',
})
export class ReturnExceptionComponent implements OnInit {
  messageError: string | null = null;
  isLoading = false;
  returnExceptions: ReturnException[] | null = null;

  constructor(private returnExceptionService: ReturnExceptionService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.messageError = null;
    this.isLoading = false;
    this.returnExceptionService.findAll().subscribe(
      response => this.returnExceptions = response,
      e => this.messageError = e.error.title
    );
  }

  delete(returnException: ReturnException): void {
    const modalRef = this.modalService.open(ReturnExceptionDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.returnException = returnException;
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
