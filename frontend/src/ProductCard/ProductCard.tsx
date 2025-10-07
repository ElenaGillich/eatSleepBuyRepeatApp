import type {Product} from "../model/Product.tsx";
import './ProductCard.css'

type ProductCardProps = {
    product: Product
}

export default function ProductCard(props: Readonly<ProductCardProps>){


    return(
            <div className={"product"}>
                <h2>{props.product.name}</h2>
                <h3>ID: {props.product.id}</h3>
            </div>
    )
}