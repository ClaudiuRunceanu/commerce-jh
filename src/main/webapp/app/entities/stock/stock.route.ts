import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockPopupComponent } from './stock-dialog.component';
import { StockDeletePopupComponent } from './stock-delete-dialog.component';

import { Principal } from '../../shared';


export const stockRoute: Routes = [
  {
    path: 'stock',
    component: StockComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'stock/:id',
    component: StockDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockPopupRoute: Routes = [
  {
    path: 'stock-new',
    component: StockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'stock/:id/edit',
    component: StockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'stock/:id/delete',
    component: StockDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
