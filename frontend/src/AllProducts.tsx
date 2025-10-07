import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import ProductCard from "./ProductCard/ProductCard.tsx";

export default function AllProducts(){
    const [products, setProducts] = useState<Product[]>([]);

    function getAllProducts() {
        axios.get('api/products').then(p => setProducts(p.data))
            .catch(e => console.log(e))
    }

    useEffect(() => {
        getAllProducts()
    }, []);
    return(
        <>
            {products?.map(p =>
                <ProductCard product={p} />
            )
            }
        </>
    )
}