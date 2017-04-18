import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { WarehouseComponent } from './warehouse.component';
import { WarehouseDetailComponent } from './warehouse-detail.component';
import { WarehousePopupComponent } from './warehouse-dialog.component';
import { WarehouseDeletePopupComponent } from './warehouse-delete-dialog.component';

import { Principal } from '../../shared';


export const warehouseRoute: Routes = [
  {
    path: 'warehouse',
    component: WarehouseComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.warehouse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'warehouse/:id',
    component: WarehouseDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.warehouse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const warehousePopupRoute: Routes = [
  {
    path: 'warehouse-new',
    component: WarehousePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.warehouse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'warehouse/:id/edit',
    component: WarehousePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.warehouse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'warehouse/:id/delete',
    component: WarehouseDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.warehouse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
