import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {returnExceptionSettingRoute} from "./return-exception-setting.route";
import {ReturnExceptionDeleteDialogComponent} from "./delete/return-exception-delete-dialog.component";
import {ReturnExceptionComponent} from "./list/return-exception.component";
import {ReturnExceptionEditComponent} from "./edit/return-exception-edit.component";
import {ReturnExceptionCopyComponent} from "./copy/return-exception-copy.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild(returnExceptionSettingRoute)],
  declarations: [
    ReturnExceptionComponent,
    ReturnExceptionEditComponent,
    ReturnExceptionCopyComponent,
    ReturnExceptionDeleteDialogComponent
  ],
})
export class ReturnExceptionSettingModule {
}
