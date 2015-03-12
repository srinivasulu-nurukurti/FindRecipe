var recipeTemplate;
var recipesTemplate;
var itemsTemplate;

var fridgeItemsSample = "\
bread,10,slices,25/12/2015\n\
cheese,10,slices,25/12/2014\n\
butter,250,grams,25/12/2014\n\
peanut butter,250,grams,2/12/2014\n\
mixed salad,150,grams,26/12/2015";

var recipesSample = [ {
  "name": "grilled cheese on toast",
  "ingredients": [
   { "item":"bread", "amount":"2", "unit":"slices"},
   { "item":"cheese", "amount":"2", "unit":"slices"}
  ]
} , {
  "name": "salad sandwich",
  "ingredients": [
   { "item":"bread", "amount":"2", "unit":"slices"},
   { "item":"mixed salad", "amount":"100", "unit":"grams"}
  ]
} ];

function loadMainFragment() {
	$("#new-body").empty();
    $("#new-body").load("main-body.html", function() {
        $("#fridge-submit").click(function(event) { callAndStop(event, updateFridge); });
        $("#fridge-sample").click(function(event) { callAndStop(event, showFridgeSample); });
        $("#fridge-clear").click(function(event) {
            hideAlertsFridge();
            setFieldHasError("fridge-text", false);
        });
        $("#recipes-submit").click(function(event) { callAndStop(event, updateRecipes); });
        $("#recipes-sample").click(function(event) { callAndStop(event, showRecipesSample); });
        $("#recipes-clear").click(function(event) {
            hideAlertsRecipes();
            setFieldHasError("recipes-text", false);
        });
    });
}

function callAndStop(event, action) {
    event.preventDefault();
    action();
}

function showFridgeSample() {
    $("#fridge-text").val(fridgeItemsSample);
}

function showRecipesSample() {
    $("#recipes-text").val(JSON.stringify(recipesSample, undefined, 2));
}

function loadFridgeFragment() {
	$("#new-body").empty();
	$("#new-body").load("fridge-body.html", function() {
        $.getJSON( "services/fridge", function() {})
            .done(function(data) { showFridgeItems(data); })
            .always(function() { $("#loading-items").hide(); });
    });
}

function loadRecipesFragment() {
    $("#new-body").empty();
    $("#new-body").load("recipes-body.html", function() {
        $.getJSON( "services/recipes", function() {})
            .done(function(data) { showRecipes(data); })
            .always(function() { $("#loading-recipes").hide(); });
    });
}

function loadSuggestionFragment() {
    $("#new-body").empty();
    $("#new-body").load("suggestion-body.html", function() {
        $.getJSON( "services/recipes/suggestion", function() {})
            .done(function(data) { showSuggestion(data); })
            .always(function() { $("#loading-suggestion").hide(); });
    });
}

function showFridgeItems(itemsList) {
    if (!itemsList || !itemsList.length)
        warning("#fridge-message", "Fridge is empty");
    else {
        if (!itemsTemplate) itemsTemplate = Handlebars.compile($("#items-template").html());
        $("#items-body").html(itemsTemplate(itemsList));
        $("#items").show();
    }
}

function showRecipes(recipes) {
    if (!recipes || !recipes.length)
        warning("#recipes-message", "No recipes");
    else {
        if (!recipesTemplate) recipesTemplate = Handlebars.compile($("#recipe-list-template").html());
        $("#recipe-list-body").html(recipesTemplate(recipes));
        $("#items").show();
    }
}

function showRecipe(recipe, target) {
    if (!recipeTemplate) recipeTemplate = Handlebars.compile($("#recipe-template").html());
    $(target).html(recipeTemplate(recipe));
    $(target).show();
}

function showSuggestion(suggestion) {
    if (!suggestion.recipe) {
        warning("#suggestion-message", suggestion.message);
    } else {
        $("#suggestion-message").hide();
        $("#suggested-recipe").show();
        showRecipe(suggestion.recipe, "#suggested-recipe");
    }
}

function warning(target, message) {
    $(target).html("<i class='fa fa-exclamation-circle'></i>&nbsp;&nbsp;" + message);
    $(target).fadeIn();
}

function updateFridge() {
    disableFridgeSubmitButton(true);
    setFieldHasError("fridge-text", false);

    var data = $("#fridge-text").val();
	
    var posting = $.ajax({
        type: "POST",
        url: "/FindRecipe/services/fridge/add",
        data: data,
        contentType: "text/plain",
    });

	
    posting.done(function(response) {
        $("#fridge-info-message").html(response.message);
        $("#fridge-info").show();
        $("#fridge-text").val("");

        if (response.recipe)
            showRecipe(response.recipe, "#dinner-suggestion-fridge");
    });

    posting.fail(function(xhr) {
        $("#fridge-error-message").html(xhr.responseText);
        $("#fridge-error").show();
        setFieldHasError("fridge-text", true);
    });

    posting.always(function() {
        disableFridgeSubmitButton(false);
    });
}

function setFieldHasError(field, hasError) {
    var formGroup = "#" + field + "-fg";
    if (hasError)
        $(formGroup).addClass("has-error");
    else
        $(formGroup).removeClass("has-error");
}

function disableFridgeSubmitButton(disable) {
    if (disable) hideAlerts();
    $("#fridge-submit").attr('disabled', disable);
}

function updateRecipes() {
    disableRecipesLoadButton(true);
    setFieldHasError("recipes-text", false);

    var data = $("#recipes-text").val();
    if (!data) {
        setFieldHasError("recipes-text", true);
        return loadRecipesFailed("No recipes were specified!");
    }
    try {
        var json = jQuery.parseJSON(data);
    } catch(err) {
        setFieldHasError("recipes-text", true);
        return loadRecipesFailed("Error parsing the specified recipes:<br /><i>" + err.message + "</i>");
    }

    var posting = $.ajax({
        url: "/FindRecipe/services/recipes/add",
        type: "POST",
        data: data,
        dataType: "json",
        contentType: "application/json"
    });

    posting.done(function(response) {
        $("#recipe-info-message").html(response.message);
        $("#recipe-info").show();
        $("#recipes-text").val("");

        if (response.recipe)
            showRecipe(response.recipe, "#dinner-suggestion-recipe");
    });

    posting.fail(function(xhr) {
        setFieldHasError("recipes-text", true);
        loadRecipesFailed(xhr.responseText);
    });

    posting.always(function() {
        disableRecipesLoadButton(false);
    });
}

function loadRecipesFailed(message) {
    $("#recipe-error-message").html(message);
    $("#recipe-error").show();
    disableRecipesLoadButton(false);
}

function disableRecipesLoadButton(disable) {
    if (disable) hideAlerts();
    $("#recipes-submit").attr('disabled', disable);
}

function hideAlerts() {
    hideAlertsFridge();
    hideAlertsRecipes();
}

function hideAlertsFridge() {
    $("#fridge-error").hide();
    $("#fridge-info").hide();
    $("#dinner-suggestion-fridge").hide();
}

function hideAlertsRecipes() {
    $("#recipe-error").hide();
    $("#recipe-info").hide();
    $("#dinner-suggestion-recipe").hide();
}