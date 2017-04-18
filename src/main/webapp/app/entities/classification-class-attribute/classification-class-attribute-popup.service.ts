import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClassificationClassAttribute } from './classification-class-attribute.model';
import { ClassificationClassAttributeService } from './classification-class-attribute.service';
@Injectable()
export class ClassificationClassAttributePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private classificationClassAttributeService: ClassificationClassAttributeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.classificationClassAttributeService.find(id).subscribe(classificationClassAttribute => {
                this.classificationClassAttributeModalRef(component, classificationClassAttribute);
            });
        } else {
            return this.classificationClassAttributeModalRef(component, new ClassificationClassAttribute());
        }
    }

    classificationClassAttributeModalRef(component: Component, classificationClassAttribute: ClassificationClassAttribute): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.classificationClassAttribute = classificationClassAttribute;
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
