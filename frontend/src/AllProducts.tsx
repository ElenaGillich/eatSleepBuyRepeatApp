import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import ProductCard from "./ProductCard/ProductCard.tsx";

type ProductListProps = {
    products: Product[]
}

export default function AllProducts(props: ProductListProps) {
    const [products, setProducts] = useState<Product[]>([]);

    function getAllProducts() {
        axios.get('api/products').then(p => setProducts(p.data))
            .catch(e => console.log(e))
    }

    const handleDeleteFromList = (deletedId: string) => {
        setProducts(prevProducts => prevProducts.filter(p => p.id !== deletedId));
    }

    useEffect(() => {
        getAllProducts()
    }, []);

    return(
        <>
            {products?.map(p =>
                    <ProductCard key={p.id} product={p} handleDeleteFromList={handleDeleteFromList}/>
            )
            }
        </>
    )
}