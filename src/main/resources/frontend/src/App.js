import { BrowserRouter, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

import Stores from "./files/ADMIN/store-pages/Stores"
import StoreDetails from "./files/ADMIN/store-pages/StoraDetails";
import Orders from "./files/ADMIN/order-pages/Orders"
import Products from "./files/ADMIN/prod-pages/Products";
import NewProduct from "./files/ADMIN/prod-pages/NewProduct";
import LayoutAdmin from "./files/ADMIN/LayoutAdmin"
import EditProduct from "./files/ADMIN/prod-pages/EditProduct";
import OrderDetails from "./files/ADMIN/order-pages/OrderDetails";
import CreateStore from "./files/ADMIN/store-pages/CreateStore";
import UpdateStore from "./files/ADMIN/store-pages/UpdateStore";
import Users from "./files/ADMIN/user-pages/Users";
import NewUser from "./files/ADMIN/user-pages/NewUser";
import UpdateUser from "./files/ADMIN/user-pages/UpdateUser";
import LayoutStore from "./files/STORE/LayoutStore";
import StoreStock from "./files/STORE/StoreStock";
import PasswordUpdate from "./files/STORE/PasswordUpdate";
import StoreInfo from "./files/STORE/StoreInfo";
import StoreOrder from "./files/STORE/StoreOrder";
import StoreOrderDetails from "./files/STORE/StoreOrderDetails";
import AddOrderRow from "./files/STORE/AddOrderRow";
import Summary from "./files/ADMIN/summary/Summary";
import SummaryByStore from "./files/ADMIN/summary/SummaryByStore";
import SummaryByProduct from "./files/ADMIN/summary/SummaryByProduct";
import SummaryByProductForPeriod from "./files/ADMIN/summary/SummaryByProductForPeriod";
import SummaryByStoreForPeriod from "./files/ADMIN/summary/SummaryByStoreForPeriod";


const App = () => {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LayoutAdmin />}>
          <Route index element={<Stores />} />
          <Route path="summary" element={<Summary/>}/>
            <Route exact path="/summary/stores/:id" element={<SummaryByStore/>} />
            <Route exact path="/summary/products/:id" element={<SummaryByProduct/>} />
            <Route path="/summary/products/by-period/:id" element={<SummaryByProductForPeriod/>} />
            <Route path="/summary/stores/by-period/:id" element={<SummaryByStoreForPeriod/>} />
          <Route path="products" element={<Products />} />
            <Route exact path = "/products/new" element = {<NewProduct/>}/>{}
            <Route exact path = "/products/update/:id" element = {<EditProduct/>}/>{}
          <Route path="stores" element={<Stores />} />
            <Route exact path = "/stores/new" element = {<CreateStore/>}/>{}
            <Route exact path = "/stores/update/:id" element = {<UpdateStore/>}/>{}
            <Route exact path = "/stores/:id" element = {<StoreDetails/>}/>{}
          <Route path="users" element={<Users />} />
            <Route exact path = "/users/new" element = {<NewUser/>}/>{}
            <Route exact path= "/users/:id" element = {<UpdateUser/>} /> {}
            <Route path="orders" element={<Orders />} />
            <Route path = "/orders/:id" element = {<OrderDetails/>}/>{}
        </Route>

        <Route path="/users/password" element={<PasswordUpdate/>}/>{}
        <Route path="/orders/store/:id" element={<StoreOrder/>}/> {}
        <Route path="/orders/rows/:id" element={<AddOrderRow/>}/> {}
        <Route path="/store/orders/:id" element={<StoreOrderDetails/>}/> {}


        <Route path = "/store/" element={<LayoutStore/>}>
          <Route index element={<StoreInfo/>}/>
          <Route path="/store/:id" element = {<StoreInfo/>}/>{}
          <Route path="/store/stocks/:id" element={<StoreStock/>}/> {}
          
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;