import {Route} from '@angular/router';
import {CalculationComponent} from "./calculation.component";

export const CALCULATION_ROUTE: Route = {
  path: '',
  component: CalculationComponent,
  data: {
    pageTitle: 'Расчет маршрута',
  },
};
