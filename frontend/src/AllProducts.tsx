import type {Product} from "./model/Product.tsx";
import ProductCard from "./ProductCard/ProductCard.tsx";

type ProductListProps = {
    products: Product[]
}

export default function AllProducts(props: ProductListProps) {

    return(
        <>
            {props.products.map(p =>
                <ProductCard key={p.id} product={p} />
            )}
        </>
    )
}