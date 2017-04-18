import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClassificationClassAttribute } from './classification-class-attribute.model';
import { ClassificationClassAttributeService } from './classification-class-attribute.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-classification-class-attribute',
    templateUrl: './classification-class-attribute.component.html'
})
export class ClassificationClassAttributeComponent implements OnInit, OnDestroy {
classificationClassAttributes: ClassificationClassAttribute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private classificationClassAttributeService: ClassificationClassAttributeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['classificationClassAttribute', 'classificationAttributeType']);
    }

    loadAll() {
        this.classificationClassAttributeService.query().subscribe(
            (res: Response) => {
                this.classificationClassAttributes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClassificationClassAttributes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ClassificationClassAttribute) {
        return item.id;
    }



    registerChangeInClassificationClassAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('classificationClassAttributeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
