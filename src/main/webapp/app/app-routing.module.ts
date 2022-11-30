import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {errorRoute} from './layouts/error/error.route';
import {navbarRoute} from './layouts/navbar/navbar.route';
import {DEBUG_INFO_ENABLED} from 'app/app.constants';
import {Authority} from 'app/config/authority.constants';

import {UserRouteAccessService} from 'app/core/auth/user-route-access.service';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: 'calculation',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./calculation/calculation.module`).then(m => m.CalculationModule),
        },
        {
          path: 'setting',
          data: {
            authorities: [Authority.ADMIN, Authority.USER],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import(`./settings/settings-routing.module`).then(m => m.SettingsRoutingModule),
        },
        {
          path: '',
          loadChildren: () => import(`./entities/entity-routing.module`).then(m => m.EntityRoutingModule),
        },
        navbarRoute,
        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
