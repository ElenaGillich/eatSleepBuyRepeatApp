import './App.css'
import axios from 'axios';
import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";

function App() {

    const [products, setProducts] = useState<Product[]>([]);

    function getAllProducts() {
        axios.get('api/products').then(p => setProducts(p.data))
            .catch(e => console.log(e))
    }

    useEffect(() => {
        getAllProducts()
    }, []);


    return (
        <>
            {products?.map(p =>
                <h1>{p.name}</h1>
            )
            }
        </>
    )
}

export default App
