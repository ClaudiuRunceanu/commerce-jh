import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CurrencyComponent } from './currency.component';
import { CurrencyDetailComponent } from './currency-detail.component';
import { CurrencyPopupComponent } from './currency-dialog.component';
import { CurrencyDeletePopupComponent } from './currency-delete-dialog.component';

import { Principal } from '../../shared';


export const currencyRoute: Routes = [
  {
    path: 'currency',
    component: CurrencyComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'currency/:id',
    component: CurrencyDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const currencyPopupRoute: Routes = [
  {
    path: 'currency-new',
    component: CurrencyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'currency/:id/edit',
    component: CurrencyPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'currency/:id/delete',
    component: CurrencyDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
