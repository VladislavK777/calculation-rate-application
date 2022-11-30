import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ReturnException} from "../service/return-exception.model";
import {ReturnExceptionService} from "../service/return-exception.service";


@Component({
  selector: 'jhi-return-exception-delete-dialog',
  templateUrl: './return-exception-delete-dialog.component.html',
})
export class ReturnExceptionDeleteDialogComponent {
  returnException?: ReturnException;

  constructor(private returnExceptionService: ReturnExceptionService, private activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.returnExceptionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
