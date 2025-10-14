import './App.css'
import {Route, Routes} from "react-router-dom";
import AllProducts from "./AllProducts.tsx";
import Navbar from "./Navbar.tsx";
import NewProduct from "./NewProduct.tsx";
import {useEffect, useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import AllGroceryLists from "./AllGroceryLists.tsx";
import CreateNewList from "./NewGroceryList/NewGroceryList.tsx";
import Login from "./Login.tsx";
import ProtectedRoute from "./ProtectedRoute.tsx";

function App() {
    const [user, setUser] = useState<string|undefined|null>(undefined);
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
            <Navbar/>
            <Routes>
                <Route path={"/"} element={<Login setUser={setUser}/>}/>
                <Route element={<ProtectedRoute user={user}/>}>
                    <Route path={"/newProduct"} element={<NewProduct/>}/>
                    <Route path={"/allProducts"} element={<AllProducts/>}/>
                    <Route path={"/home"} element={<AllGroceryLists/>}/>
                    <Route path={"/addGroceryList"} element={<CreateNewList products={products}/>}/>
                </Route>
            </Routes>
        </>
    )
}

export default App
