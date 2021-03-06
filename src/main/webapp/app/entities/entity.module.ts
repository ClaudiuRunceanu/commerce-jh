import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CommerceCurrencyModule } from './currency/currency.module';
import { CommercePriceModule } from './price/price.module';
import { CommerceCatalogModule } from './catalog/catalog.module';
import { CommerceCategoryModule } from './category/category.module';
import { CommerceClassificationClassAttributeModule } from './classification-class-attribute/classification-class-attribute.module';
import { CommerceStockModule } from './stock/stock.module';
import { CommerceWarehouseModule } from './warehouse/warehouse.module';
import { CommerceProductModule } from './product/product.module';
import { CommerceMediaModule } from './media/media.module';
import { CommerceCustomFilterModule } from './custom-filter/custom-filter.module';
import { CommerceOrderEntryModule } from './order-entry/order-entry.module';
import { CommerceCartModule } from './cart/cart.module';
import { CommerceCustomerOrderModule } from './customer-order/customer-order.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CommerceCurrencyModule,
        CommercePriceModule,
        CommerceCatalogModule,
        CommerceCategoryModule,
        CommerceClassificationClassAttributeModule,
        CommerceStockModule,
        CommerceWarehouseModule,
        CommerceProductModule,
        CommerceMediaModule,
        CommerceCustomFilterModule,
        CommerceOrderEntryModule,
        CommerceCartModule,
        CommerceCustomerOrderModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceEntityModule {}
