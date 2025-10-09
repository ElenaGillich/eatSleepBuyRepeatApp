import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import ProductCard from "./ProductCard/ProductCard.tsx";
import {useNavigate} from "react-router-dom";
export default function AllProducts() {
    const [products, setProducts] = useState<Product[]>([]);

    const nav = useNavigate();

    function getAllProducts() {
        axios.get('api/products').then(p => setProducts(p.data))
            .catch(e => console.log(e))
    }

    function goToNewProductForm(){
        nav("/newProduct")
    }
    useEffect(() => {
        getAllProducts()
    }, []);

    return(
        <>
            <button onClick={goToNewProductForm}>New Product</button>
            {products.map(p =>
                <ProductCard key={p.id} product={p} />
            )}
        </>
    )
}