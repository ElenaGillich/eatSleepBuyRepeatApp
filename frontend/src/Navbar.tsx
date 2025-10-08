import {Link} from "react-router-dom";

export default function Navbar(){
    return (
        <>
            <Link to={"/allProducts"}>All Products</Link>
            <Link to={"/allGroceryLists"}>All Grocery Lists</Link>
        </>
    )
}