import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomFilter } from './custom-filter.model';
import { CustomFilterService } from './custom-filter.service';
@Injectable()
export class CustomFilterPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private customFilterService: CustomFilterService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customFilterService.find(id).subscribe(customFilter => {
                this.customFilterModalRef(component, customFilter);
            });
        } else {
            return this.customFilterModalRef(component, new CustomFilter());
        }
    }

    customFilterModalRef(component: Component, customFilter: CustomFilter): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customFilter = customFilter;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
