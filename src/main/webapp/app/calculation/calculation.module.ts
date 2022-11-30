import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {CALCULATION_ROUTE} from "./calculation.route";
import {CalculationComponent} from "./calculation.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild([CALCULATION_ROUTE])],
  declarations: [CalculationComponent],
})
export class CalculationModule {}
