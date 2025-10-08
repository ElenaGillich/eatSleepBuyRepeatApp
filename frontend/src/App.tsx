import './App.css'
import {Route, Routes} from "react-router-dom";
import AllProducts from "./AllProducts.tsx";
import Navbar from "./Navbar.tsx";
import AllGroceryLists from "./AllGroceryLists.tsx";

function App() {


    return (
        <>
            <Navbar/>
            <Routes>
                <Route path={"/allProducts"} element={<AllProducts/>}/>
                <Route path={"/allGroceryLists"} element={<AllGroceryLists/>}/>
            </Routes>

        </>
    )
}

export default App
