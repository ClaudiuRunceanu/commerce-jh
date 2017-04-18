import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CatalogComponent } from './catalog.component';
import { CatalogDetailComponent } from './catalog-detail.component';
import { CatalogPopupComponent } from './catalog-dialog.component';
import { CatalogDeletePopupComponent } from './catalog-delete-dialog.component';

import { Principal } from '../../shared';


export const catalogRoute: Routes = [
  {
    path: 'catalog',
    component: CatalogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.catalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'catalog/:id',
    component: CatalogDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.catalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const catalogPopupRoute: Routes = [
  {
    path: 'catalog-new',
    component: CatalogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.catalog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'catalog/:id/edit',
    component: CatalogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.catalog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'catalog/:id/delete',
    component: CatalogDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.catalog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
