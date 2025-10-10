import {Link} from "react-router-dom";

export default function Navbar() {

    return (

        <header>
            <nav className={"navbar"}>
                <Link className={"nav-button"} to={"/"}>Home</Link>
                <Link className={"nav-button"} to={"/allProducts"}>All Products</Link>
                <Link className={"nav-button"} to={"/allGroceryLists"}>All Grocery Lists</Link>
            </nav>
        </header>
    )
}