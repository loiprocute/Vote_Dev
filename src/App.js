import logo from './logo.svg';
import './App.css';
import WalletBasic  from './component/wallet';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <WalletBasic/>
        {/* <MyComponent/> */}
        {/*<MyComponent></MyComponent> */}
      </header>
    </div>
  );
}

export default App;