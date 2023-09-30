package pl.hodana.vehicles_thymeleaf.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hodana.vehicles_thymeleaf.model.Vehicle;
import pl.hodana.vehicles_thymeleaf.service.VehiclesService;

import java.util.Optional;

@Controller
public class VehiclesApi {
    private final VehiclesService vehiclesService;


    public VehiclesApi(VehiclesService vehicleService) {
        this.vehiclesService = vehicleService;
    }

    @GetMapping("/vehicle")
    public String getVehicles(Model model) {
        model.addAttribute("vehicles", vehiclesService.getVehicles());
        model.addAttribute("newVehicle", new Vehicle());
        return "vehicle";
    }

    @GetMapping("/vehicle/{id}")
    public String getVehiclesById(@PathVariable long id, Model model) {
        Optional<Vehicle> searchedVehicle = vehiclesService.getVehiclesById(id);
        if (!searchedVehicle.isPresent())
            model.addAttribute("VehicleNotFound", "Vehicle with this id is not exist");

        model.addAttribute("vehicle", searchedVehicle.get());
        return "vehicleById";
    }

    @PostMapping("/add-vehicle")
    public String addVehicle(@Validated @ModelAttribute Vehicle vehicle, RedirectAttributes redirectAttributes) {
        if (!vehiclesService.addVehicle(vehicle))
            redirectAttributes.addFlashAttribute("NotAdded", "Vehicle add failed");
        return "redirect:/vehicle";

    }

    @PutMapping("modify-vehicle")
    public String modificationVehicle(@Validated @ModelAttribute Vehicle vehicle, RedirectAttributes redirectAttributes) {
        if (!vehiclesService.modificationVehicle(vehicle))
            redirectAttributes.addFlashAttribute("NotModify", "Vehicle modify failed");
        return "redirect:/vehicle";
    }

    @PatchMapping("/modify-color")
    public String modificationColorById(long id, @RequestParam @NotNull String color, RedirectAttributes redirectAttributes) {
        if (!vehiclesService.modificationColor(id, color))
            redirectAttributes.addFlashAttribute("NotModifyColor", "Color modify failed");
        return "redirect:/vehicle";
    }

    @DeleteMapping("delete-vehicle")
    public String removeVehicle(long id, RedirectAttributes redirectAttributes) {
        if (!vehiclesService.removeVehicle(id))
            redirectAttributes.addFlashAttribute("NotDelete", "Vehicle with this id is not exist");
        return "redirect:/vehicle";
    }

}