<div id="login-modal" class="modal hidden">
	<div class="modal-content">
		<span id="login-close">&times;</span>

		<h2>Login</h2>

		<form id="login-form">
			<input type="text" name="name" placeholder="Name" required> 
			<input type="email" name="email" placeholder="Email" required> 
			<input type="password" name="password" autocomplete="current-password" required>

			<button type="submit">Login</button>
		</form>

		<p id="login-error" class="error"></p>
	</div>
	<div id="login-toast" class="toast hidden">
    Logged in successfully
</div>
</div>
