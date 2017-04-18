import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClassificationClassAttributeComponent } from './classification-class-attribute.component';
import { ClassificationClassAttributeDetailComponent } from './classification-class-attribute-detail.component';
import { ClassificationClassAttributePopupComponent } from './classification-class-attribute-dialog.component';
import { ClassificationClassAttributeDeletePopupComponent } from './classification-class-attribute-delete-dialog.component';

import { Principal } from '../../shared';


export const classificationClassAttributeRoute: Routes = [
  {
    path: 'classification-class-attribute',
    component: ClassificationClassAttributeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.classificationClassAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'classification-class-attribute/:id',
    component: ClassificationClassAttributeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.classificationClassAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const classificationClassAttributePopupRoute: Routes = [
  {
    path: 'classification-class-attribute-new',
    component: ClassificationClassAttributePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.classificationClassAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'classification-class-attribute/:id/edit',
    component: ClassificationClassAttributePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.classificationClassAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'classification-class-attribute/:id/delete',
    component: ClassificationClassAttributeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'commerceApp.classificationClassAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
