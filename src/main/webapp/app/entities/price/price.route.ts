import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PriceComponent } from './price.component';
import { PriceDetailComponent } from './price-detail.component';
import { PricePopupComponent } from './price-dialog.component';
import { PriceDeletePopupComponent } from './price-delete-dialog.component';

import { Principal } from '../../shared';


export const priceRoute: Routes = [
  {
    path: 'price',
    component: PriceComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.price.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'price/:id',
    component: PriceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.price.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pricePopupRoute: Routes = [
  {
    path: 'price-new',
    component: PricePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.price.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'price/:id/edit',
    component: PricePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.price.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'price/:id/delete',
    component: PriceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.price.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
