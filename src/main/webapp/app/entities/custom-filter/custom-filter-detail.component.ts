import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { CustomFilter } from './custom-filter.model';
import { CustomFilterService } from './custom-filter.service';

@Component({
    selector: 'jhi-custom-filter-detail',
    templateUrl: './custom-filter-detail.component.html'
})
export class CustomFilterDetailComponent implements OnInit, OnDestroy {

    customFilter: CustomFilter;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customFilterService: CustomFilterService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['customFilter']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInCustomFilters();
    }

    load (id) {
        this.customFilterService.find(id).subscribe(customFilter => {
            this.customFilter = customFilter;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomFilters() {
        this.eventSubscriber = this.eventManager.subscribe('customFilterListModification', response => this.load(this.customFilter.id));
    }

}
