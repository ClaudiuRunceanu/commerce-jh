import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

import { Media } from './media.model';
import { MediaService } from './media.service';

@Component({
    selector: 'jhi-media-detail',
    templateUrl: './media-detail.component.html'
})
export class MediaDetailComponent implements OnInit, OnDestroy {

    media: Media;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private mediaService: MediaService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['media']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInMedia();
    }

    load (id) {
        this.mediaService.find(id).subscribe(media => {
            this.media = media;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMedia() {
        this.eventSubscriber = this.eventManager.subscribe('mediaListModification', response => this.load(this.media.id));
    }

}
