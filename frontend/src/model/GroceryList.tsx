import type {Status} from "./Status.ts";
import type {ProductListItem} from "./ProductListItem.ts";

export type GroceryList = {
    id: string,
    products: ProductListItem[],
    status: Status;
}