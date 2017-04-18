import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Warehouse } from './warehouse.model';
import { WarehouseService } from './warehouse.service';

@Component({
    selector: 'jhi-warehouse-detail',
    templateUrl: './warehouse-detail.component.html'
})
export class WarehouseDetailComponent implements OnInit, OnDestroy {

    warehouse: Warehouse;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private warehouseService: WarehouseService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['warehouse']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInWarehouses();
    }

    load (id) {
        this.warehouseService.find(id).subscribe(warehouse => {
            this.warehouse = warehouse;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWarehouses() {
        this.eventSubscriber = this.eventManager.subscribe('warehouseListModification', response => this.load(this.warehouse.id));
    }

}
