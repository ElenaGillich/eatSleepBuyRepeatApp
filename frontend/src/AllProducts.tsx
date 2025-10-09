import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import ProductCard from "./ProductCard/ProductCard.tsx";
import DeleteButton from "./ProductCard/DeleteButton.tsx";

export default function AllProducts() {
    const [products, setProducts] = useState<Product[]>([]);

    function getAllProducts() {
        axios.get('api/products').then(p => setProducts(p.data))
            .catch(e => console.log(e))
    }

    const handleDelete = (deletedId: string) => {
        setProducts(prevProducts => prevProducts.filter(p => p.id !== deletedId));
    };

    useEffect(() => {
        getAllProducts()
    }, []);

    return(
        <>
            {products?.map(p =>
                <div>
                    <ProductCard key={p.id} product={p} />
                    <DeleteButton productId={p.id} onDelete={handleDelete}/>
                </div>
            )
            }
        </>
    )
}