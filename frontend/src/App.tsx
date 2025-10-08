import './App.css'
import {Route, Routes} from "react-router-dom";
import AllProducts from "./AllProducts.tsx";
import Navbar from "./Navbar.tsx";
import NewProduct from "./NewProduct.tsx";

function App() {


    return (
        <>
            <Navbar/>
            <Routes>
                <Route path={"/allProducts"} element={<AllProducts/>}/>
                <Route path={"/newProduct"} element={<NewProduct/>}/>
            </Routes>

        </>
    )
}

export default App
