import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderEntryComponent } from './order-entry.component';
import { OrderEntryDetailComponent } from './order-entry-detail.component';
import { OrderEntryPopupComponent } from './order-entry-dialog.component';
import { OrderEntryDeletePopupComponent } from './order-entry-delete-dialog.component';

import { Principal } from '../../shared';


export const orderEntryRoute: Routes = [
  {
    path: 'order-entry',
    component: OrderEntryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.orderEntry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-entry/:id',
    component: OrderEntryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.orderEntry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderEntryPopupRoute: Routes = [
  {
    path: 'order-entry-new',
    component: OrderEntryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.orderEntry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-entry/:id/edit',
    component: OrderEntryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.orderEntry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-entry/:id/delete',
    component: OrderEntryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.orderEntry.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
