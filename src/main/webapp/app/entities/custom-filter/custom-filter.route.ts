import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomFilterComponent } from './custom-filter.component';
import { CustomFilterDetailComponent } from './custom-filter-detail.component';
import { CustomFilterPopupComponent } from './custom-filter-dialog.component';
import { CustomFilterDeletePopupComponent } from './custom-filter-delete-dialog.component';

import { Principal } from '../../shared';


export const customFilterRoute: Routes = [
  {
    path: 'custom-filter',
    component: CustomFilterComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.customFilter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'custom-filter/:id',
    component: CustomFilterDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.customFilter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customFilterPopupRoute: Routes = [
  {
    path: 'custom-filter-new',
    component: CustomFilterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.customFilter.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'custom-filter/:id/edit',
    component: CustomFilterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.customFilter.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'custom-filter/:id/delete',
    component: CustomFilterDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.customFilter.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
