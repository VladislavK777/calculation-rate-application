import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {BeginException} from "../service/begin-exception.model";
import {BeginExceptionService} from "../service/begin-exception.service";


@Component({
  selector: 'jhi-begin-exception-delete-dialog',
  templateUrl: './begin-exception-delete-dialog.component.html',
})
export class BeginExceptionDeleteDialogComponent {
  beginException?: BeginException;

  constructor(private beginExceptionService: BeginExceptionService, private activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beginExceptionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
