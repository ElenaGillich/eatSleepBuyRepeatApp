import './App.css'
import './index.css'
import {Route, Routes} from "react-router-dom";
import AllProducts from "./AllProducts.tsx";
import Navbar from "./Navbar.tsx";
import NewProduct from "./NewProduct.tsx";
import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import AllGroceryLists from "./AllGroceryLists.tsx";
import CreateNewList from "./NewGroceryList/NewGroceryList.tsx";

function App() {
    const [products, setProducts] = useState<Product[]>([]);
    useEffect(() => {
        loadAllProducts()
    }, []);

    function loadAllProducts() {
        axios.get("api/products")
            .then(result => setProducts(result.data ?? []))
            .catch((error) => console.log("No products found! ", error))
    }

    return (
        <>
            <div className="area" aria-hidden="true">
                <ul className="circles">
                    <li /><li /><li /><li /><li />
                    <li /><li /><li /><li /><li />
                </ul>
            </div>

            <Navbar/>
            <Routes>
                <Route path={"/newProduct"} element={<NewProduct/>}/>
                <Route path={"/allProducts"} element={<AllProducts/>}/>
                <Route path={"/allGroceryLists"} element={<AllGroceryLists/>}/>
                <Route path={"/addGroceryList"} element={<CreateNewList products={products}/>}/>
            </Routes>

        </>
    )
}

export default App
