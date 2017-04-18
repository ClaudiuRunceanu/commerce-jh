import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Stock } from './stock.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class StockService {

    private resourceUrl = 'api/stocks';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(stock: Stock): Observable<Stock> {
        let copy: Stock = Object.assign({}, stock);
        copy.expireDate = this.dateUtils.toDate(stock.expireDate);
        copy.creationDate = this.dateUtils.toDate(stock.creationDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(stock: Stock): Observable<Stock> {
        let copy: Stock = Object.assign({}, stock);

        copy.expireDate = this.dateUtils.toDate(stock.expireDate);

        copy.creationDate = this.dateUtils.toDate(stock.creationDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Stock> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.expireDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.expireDate);
            jsonResponse.creationDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.creationDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].expireDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].expireDate);
            jsonResponse[i].creationDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].creationDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
