import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ClassificationClassAttribute } from './classification-class-attribute.model';
import { ClassificationClassAttributeService } from './classification-class-attribute.service';

@Component({
    selector: 'jhi-classification-class-attribute-detail',
    templateUrl: './classification-class-attribute-detail.component.html'
})
export class ClassificationClassAttributeDetailComponent implements OnInit, OnDestroy {

    classificationClassAttribute: ClassificationClassAttribute;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private classificationClassAttributeService: ClassificationClassAttributeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['classificationClassAttribute', 'classificationAttributeType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInClassificationClassAttributes();
    }

    load (id) {
        this.classificationClassAttributeService.find(id).subscribe(classificationClassAttribute => {
            this.classificationClassAttribute = classificationClassAttribute;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClassificationClassAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('classificationClassAttributeListModification', response => this.load(this.classificationClassAttribute.id));
    }

}
